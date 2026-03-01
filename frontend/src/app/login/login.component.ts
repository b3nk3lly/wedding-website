import {Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'login-component',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  protected readonly title = signal('frontend');

  protected form: FormGroup;
  private redirectUrl: string;

  constructor(private fb: FormBuilder, private authService: AuthService, private route: ActivatedRoute, private router: Router) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    })

    this.redirectUrl = this.route.snapshot.params['redirect'] || '/';
  }

  protected onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    this.authService
      .doLogin(this.form.value.username, this.form.value.password)
      .subscribe(response => {
        this.router.navigateByUrl(this.redirectUrl);
      });
  }
}
