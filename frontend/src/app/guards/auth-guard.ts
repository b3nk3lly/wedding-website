import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync, RedirectCommand,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) {}

  public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if (this.authService.isAuthenticated()) {
      console.log("Authenticated!")
      return true;
    }

    const loginPath = this.router.parseUrl("/login");
    return new RedirectCommand(loginPath, {skipLocationChange: true});
  }
}
