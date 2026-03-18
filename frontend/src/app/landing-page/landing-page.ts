import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

export interface UserGroup {
  groupName: string;
  guests: string[];
  rsvpDeadline: string;
}

export interface RsvpStatus {
  submitted: boolean;
  attendingCount: number | null;
}

// In a real app, these come from AuthService / RsvpService
const MOCK_USER: UserGroup = {
  groupName: 'The Johnson Family',
  guests: ['Emily Johnson', 'Mark Johnson', 'Sophie Johnson'],
  rsvpDeadline: 'August 1st, 2025',
};

const MOCK_RSVP_STATUS: RsvpStatus = {
  submitted: false,
  attendingCount: null,
};

@Component({
  selector: 'app-landing-page',
  imports: [RouterLink],
  templateUrl: './landing-page.html',
  styleUrls: ['./landing-page.scss'],
})
export class LandingPage implements OnInit {
  private readonly router = inject(Router);

  protected userGroup = signal<UserGroup>(MOCK_USER);
  protected rsvpStatus = signal<RsvpStatus>(MOCK_RSVP_STATUS);

  protected hasRsvped = computed(() => this.rsvpStatus().submitted);
  protected guestCount = computed(() => this.userGroup().guests.length);
  protected firstName = computed(() => this.userGroup().groupName.split(' ')[1] ?? this.userGroup().groupName);

  ngOnInit(): void {
    // Replace with actual service calls, e.g.:
    // toSignal(this.rsvpService.getStatus()) assigned to rsvpStatus
  }

  protected navigateToRsvp(): void {
    this.router.navigate(['/rsvp']);
  }

  protected logout(): void {
    // this.authService.logout();
    this.router.navigate(['/login']);
  }
}
