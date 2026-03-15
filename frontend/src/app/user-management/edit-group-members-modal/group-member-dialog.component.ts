import {Component, inject, OnInit, signal} from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {GroupResponseDto, GroupService, GuestResponseDto} from '../../services/group.service';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import {GuestService} from '../../services/guest.service';

@Component({
  selector: 'edit-group-members-dialog',
  templateUrl: './group-member-dialog.component.html',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
  ],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { hideRequiredMarker: true, appearance: 'outline', floatLabel: 'never' },
    },
  ],
})
export class GroupMemberDialogComponent implements OnInit {
  protected form: FormGroup;
  protected readonly data: { group: GroupResponseDto, guest: GuestResponseDto } = inject(MAT_DIALOG_DATA);
  protected readonly isInEditMode = signal(!!this.data.guest);

  private readonly guestService = inject(GuestService);
  private readonly dialogRef: MatDialogRef<GroupMemberDialogComponent> = inject(MatDialogRef);

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: [null, [Validators.required]],
    });
  }

  public ngOnInit() {
    if (this.data) {
      this.form.patchValue({
        name: this.data.guest.name
      })
    }
  }

  protected createGroup() {
    return this.guestService
      .addMemberToGroup(this.data.group.id, this.form.controls['name'].value)
      .subscribe((response) => {
        console.log('Successfully created group');
        this.dialogRef.close(response);
      });
  }
}
