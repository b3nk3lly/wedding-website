import {Component, inject, OnInit, resource, signal} from '@angular/core';
import {AddGroupDialogComponent} from './add-group-modal/add-group-dialog.component';
import {GroupResponseDto, GroupService} from '../services/group.service';
import {MatIcon} from '@angular/material/icon';
import {
  MatAccordion,
  MatExpansionPanel, MatExpansionPanelActionRow,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from '@angular/material/expansion';
import {MatButton} from '@angular/material/button';
import {MatDialog} from '@angular/material/dialog';
import {EditGroupMembersDialogComponent} from './edit-group-members-modal/edit-group-members-dialog.component';

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
    MatExpansionPanelActionRow
  ],
  styleUrls: ['./group-management.component.scss']
})
export class GroupManagementComponent implements OnInit {
    protected groups = signal<GroupResponseDto[]>([]);

    private groupService = inject(GroupService);
    private dialog = inject(MatDialog);

  public ngOnInit() {
      this.fetchGroups();
  }

  protected showAddGroupModal() {
      const dialogRef = this.dialog.open(AddGroupDialogComponent, {});
      dialogRef.afterClosed().subscribe(result => {
        console.log(result);
        this.fetchGroups();
      });
  }

  protected showEditGroupMembersModal(group: GroupResponseDto) {
    const dialogRef = this.dialog.open(EditGroupMembersDialogComponent, {data: {group}})
  }

  private fetchGroups() {
    this.groupService.getAllGroups().subscribe(groups => this.groups.set(groups.sort((a, b) => a.name.localeCompare(b.name))));
  }
}
