import {Component, inject} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {GroupResponseDto, GroupService} from '../../services/group.service';
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
  selector: 'edit-group-members-dialog',
  templateUrl: './edit-group-members-dialog.component.html',
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
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {hideRequiredMarker: true, appearance: 'outline', floatLabel: 'never'}}
  ]
})
export class EditGroupMembersDialogComponent {
  protected form: FormArray;
  protected data: {group: GroupResponseDto} = inject(MAT_DIALOG_DATA)

  private dialogRef: MatDialogRef<EditGroupMembersDialogComponent> = inject(MatDialogRef);

  constructor(private fb: FormBuilder) {
    this.form = this.fb.array<string>([]);
  }

  protected addMember() {
    this.form.push(this.fb.control(''))
  }

  protected removeMember(index: number) {
    this.form.removeAt(index);
  }
}
