import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { GroupManagementComponent } from './user-management/group-management.component';
import { AuthGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  //{ path: 'group-management', component: GroupManagementComponent, canActivate: [AuthGuard] },
  { path: 'group-management', component: GroupManagementComponent },
];
