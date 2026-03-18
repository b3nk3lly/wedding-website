import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { GroupManagementComponent } from './user-management/group-management.component';
import { AuthGuard } from './guards/auth-guard';
import { LandingPage } from './landing-page/landing-page';
import { RsvpComponent } from './test-rsvp/rsvp.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: LandingPage, canActivate: [AuthGuard] },
  { path: 'rsvp', component: RsvpComponent, canActivate: [AuthGuard] },
  { path: 'group-management', component: GroupManagementComponent, canActivate: [AuthGuard] },
];
