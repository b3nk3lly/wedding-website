import { Component, signal } from '@angular/core';
import { Group } from '../../models/guest.model';
import { MatButtonModule } from '@angular/material/button';
import { RsvpForm } from '../rsvp-form/rsvp-form';

const testGroup: Group = {
  name: 'Test Group',
  members: [
    {
      name: 'Guest 1',
    },
    {
      name: 'Guest 2',
    },
  ],
};

@Component({
  selector: 'app-landing-page',
  imports: [MatButtonModule, RsvpForm],
  templateUrl: './landing-page.html',
  styleUrl: './landing-page.scss',
})
export class LandingPage {
  protected group = signal<Group>(testGroup);

  protected get hasCompletedRsvp(): boolean {
    return !this.group().members.some(
      (member) => member.isAttending == undefined || member.selectedMealId == undefined,
    );
  }
}
