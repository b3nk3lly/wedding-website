import {Component, inject, OnInit, signal} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RsvpForm } from '../rsvp-form/rsvp-form';
import {AttendanceSelection, Group} from '../models/guest.model';
import {GroupResponseDto, GroupService} from '../services/group.service';
import {toSignal} from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-landing-page',
  imports: [MatButtonModule, RsvpForm],
  templateUrl: './landing-page.html',
  styleUrl: './landing-page.scss',
})
export class LandingPage {
  private readonly groupService = inject(GroupService);

  protected group = toSignal(this.groupService.getCurrentUserGroup());
}
