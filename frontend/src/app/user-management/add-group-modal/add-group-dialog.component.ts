import {Component, inject} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {GroupService} from '../../services/group.service';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions, MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';

@Component({
  selector: 'add-group-modal',
  templateUrl: './add-group-dialog.component.html',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose
  ],
  styleUrls: ['./add-group-dialog.component.scss'],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {hideRequiredMarker: true, appearance: 'outline', floatLabel: 'never'}}
  ]
})
export class AddGroupDialogComponent {
  protected form: FormGroup;

  private groupService = inject(GroupService);
  private dialogRef: MatDialogRef<AddGroupDialogComponent> = inject(MatDialogRef);

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  protected createGroup() {
    return this.groupService.createGroup({
      name: this.form.controls["name"].value,
      username: this.form.controls["username"].value,
      password: this.form.controls["password"].value,
    }).subscribe(response => {
      console.log("Successfully created group");
      this.dialogRef.close(response);
    });
  }
}
