import {Component} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'add-user-modal',
  templateUrl: './add-user-modal.component.html',
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./add-user-modal.component.scss']
})
export class AddUserModalComponent {
  protected form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
      guests: this.fb.array<string>([])
    });
  }

  protected get guests(): FormArray {
    return this.form.get('guests') as FormArray;
  }
}
