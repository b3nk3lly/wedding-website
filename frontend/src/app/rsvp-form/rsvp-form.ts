import { Component, inject, input, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonToggleGroup, MatButtonToggle } from '@angular/material/button-toggle';
import { MatRadioButton, MatRadioGroup } from '@angular/material/radio';
import { MatError, MatInput, MatInputModule, MatLabel } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { Group, Guest, AttendanceSelection } from '../models/guest.model';
import { GroupResponseDto, GuestResponseDto } from '../services/group.service';

@Component({
  selector: 'app-rsvp-form',
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatDividerModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonToggleGroup,
    MatButtonToggle,
    MatRadioButton,
    MatRadioGroup,
  ],
  templateUrl: './rsvp-form.html',
  styleUrl: './rsvp-form.scss',
})
export class RsvpForm implements OnInit {
  public group = input.required<GroupResponseDto>();

  private fb = inject(FormBuilder);

  protected AttendanceSelection = AttendanceSelection;
  protected submitted = signal(false);

  protected form = this.fb.array([
    this.fb.group({
      isAttending: this.fb.control<AttendanceSelection | null>(null),
      mealId: this.fb.control<number | null>(null),
      hasAllergies: this.fb.control<boolean | null>(null),
      allergies: this.fb.control(''),
    }),
  ]);

  public ngOnInit(): void {
    this.form.clear();

    this.group().members.forEach((guest) => {
      this.form.push(this.createNewGuestForm(guest));
    });

    this.form.valueChanges.subscribe((value) => console.log(value));
  }

  protected submit() {
    this.submitted.set(true);
  }

  private createNewGuestForm(guest: GuestResponseDto): FormGroup {
    const guestForm = this.fb.group({
      isAttending: [guest.isAttending, Validators.required],
      mealId: this.fb.control(guest.selectedMealId ?? null),
      hasAllergies: this.fb.control(guest.hasAllergies),
      allergies: this.fb.control(guest.allergies),
    });

    guestForm.controls.isAttending.valueChanges.subscribe((isAttending) => {
      if (isAttending === AttendanceSelection.YES) {
        guestForm.controls.mealId.setValidators([Validators.required]);
        guestForm.controls.hasAllergies.setValidators([Validators.required]);
      } else {
        guestForm.controls.mealId.clearValidators();
        guestForm.controls.hasAllergies.clearValidators();
        guestForm.controls.allergies.clearValidators();
      }

      guestForm.controls.mealId.updateValueAndValidity();
      guestForm.controls.hasAllergies.updateValueAndValidity();
    });

    guestForm.controls.hasAllergies.valueChanges.subscribe((hasAllergies) => {
      if (hasAllergies) {
        guestForm.controls.allergies.setValidators([Validators.required]);
      } else {
        guestForm.controls.allergies.clearValidators();
      }

      guestForm.controls.allergies.updateValueAndValidity();
    });

    return guestForm;
  }
}
