import {Component, computed, inject, OnInit, resource, signal} from '@angular/core';
import { AddGroupDialogComponent } from './add-group-modal/add-group-dialog.component';
import {GroupResponseDto, GroupService, GuestResponseDto} from '../services/group.service';
import { MatIcon } from '@angular/material/icon';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelActionRow,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle,
} from '@angular/material/expansion';
import { MatButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { GroupMemberDialogComponent } from './edit-group-members-modal/group-member-dialog.component';
import {GuestService} from '../services/guest.service';
import {RemoveMemberDialogComponent} from './delete-group-member-modal/remove-member-dialog.component';

@Component({
  selector: 'user-management',
  templateUrl: './group-management.component.html',
  imports: [
    MatIcon,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatExpansionPanelDescription,
    MatAccordion,
    MatButton,
  ],
  styleUrls: ['./group-management.component.scss'],
})
export class GroupManagementComponent implements OnInit {
  protected readonly groups = signal<GroupResponseDto[]>([]);
  protected readonly totalPeople = computed(() => this.groups().reduce((total, group) => total + group.members.length, 0));

  private readonly groupService = inject(GroupService);
  private readonly dialog = inject(MatDialog);

  public ngOnInit() {
    this.fetchGroups();
  }

  protected showAddGroupModal() {
    const dialogRef = this.dialog.open(AddGroupDialogComponent, {});
    dialogRef.afterClosed().subscribe((result) => {
      console.log(result);
      this.fetchGroups();
    });
  }

  protected showAddGroupMemberModal(group: GroupResponseDto) {
    const dialogRef = this.dialog.open(GroupMemberDialogComponent, { data: { group }});
    dialogRef.afterClosed().subscribe((result) => {
      this.fetchGroups();
    });
  }

  protected showDeleteGroupMemberModal(group: GroupResponseDto, guest: GuestResponseDto) {
    const dialogRef = this.dialog.open(RemoveMemberDialogComponent, { data: { group, guest }});
    dialogRef.afterClosed().subscribe((result) => {
      this.fetchGroups();
    });
  }

  protected showEditGroupMemberModal(group: GroupResponseDto, guest: GuestResponseDto) {
    const dialogRef = this.dialog.open(GroupMemberDialogComponent, { data: { group, guest } });
  }

  private fetchGroups() {
    this.groupService
      .getAllGroups()
      .subscribe((groups) => this.groups.set(groups.sort((a, b) => a.name.localeCompare(b.name))));
  }
}
