import {Component, inject, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {Router} from '@angular/router';

export interface Guest {
  name: string;
  meal: string;
  hasAllergy: boolean;
  allergies: string;
}

export interface RsvpSubmission {
  attending: boolean;
  guests: Guest[];
}

// In a real app this would come from your auth service / user context
const MOCK_GROUP = {
  groupName: 'The Johnson Family',
  guests: ['Emily Johnson', 'Mark Johnson', 'Sophie Johnson'],
};

@Component({
  selector: 'app-rsvp',
  imports: [ReactiveFormsModule],
  templateUrl: './rsvp.component.html',
  styleUrls: ['./rsvp.component.scss'],
})
export class RsvpComponent implements OnInit {
  form!: FormGroup;
  protected readonly submitted = signal(false);
  groupName = MOCK_GROUP.groupName;
  guestNames = MOCK_GROUP.guests;

  protected readonly meals = [
    { value: 'beef', label: 'Beef Tenderloin', description: 'with red wine jus & truffle mash' },
    {
      value: 'salmon',
      label: 'Pan-Seared Salmon',
      description: 'with lemon beurre blanc & asparagus',
    },
    {
      value: 'vegetarian',
      label: 'Wild Mushroom Risotto',
      description: 'with parmesan & fresh herbs (V)',
    },
  ];

  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);

  public ngOnInit(): void {
    this.form = this.fb.group({
      attending: [null, Validators.required],
      guests: this.fb.array(
        this.guestNames.map((name) =>
          this.fb.group({
            name: [{ value: name, disabled: true }],
            attending: [false],
            meal: [''],
            hasAllergy: [false],
            allergies: [''],
          }),
        ),
      ),
    });

    // When global attending changes to 'no', uncheck all guests
    this.form.get('attending')?.valueChanges.subscribe((val) => {
      if (val === 'no') {
        this.guestArray.controls.forEach((ctrl) => {
          ctrl.get('attending')?.setValue(false);
          ctrl.get('meal')?.clearValidators();
          ctrl.get('meal')?.updateValueAndValidity();
        });
      }
    });

    // Per-guest: require meal when attending; clear when not
    this.guestArray.controls.forEach((ctrl) => {
      ctrl.get('attending')?.valueChanges.subscribe((attending) => {
        const mealCtrl = ctrl.get('meal');
        if (attending) {
          mealCtrl?.setValidators(Validators.required);
        } else {
          mealCtrl?.clearValidators();
          mealCtrl?.setValue('');
        }
        mealCtrl?.updateValueAndValidity();
      });
    });
  }

  protected get guestArray(): FormArray {
    return this.form.get('guests') as FormArray;
  }

  protected guestGroup(i: number): FormGroup {
    return this.guestArray.at(i) as FormGroup;
  }

  protected get isAttending(): boolean {
    return this.form.get('attending')?.value === 'yes';
  }

  protected get attendingGuests(): number {
    return this.guestArray.controls.filter((c) => c.get('attending')?.value).length;
  }

  protected onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.submitted.set(true);
    // Emit / POST to your backend here
    console.log('RSVP submission:', this.form.getRawValue());
  }

  protected resetForm(): void {
    this.submitted.set(false);
    this.form.reset({ attending: null });
  }

  protected navigateToLandingPage(): void {
    this.router.navigate(['/home']);
  }
}
