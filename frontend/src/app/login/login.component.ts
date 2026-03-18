import {Component, inject, OnInit, signal} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {AuthService} from '../services/auth.service';
import {finalize} from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  protected readonly isLoading = signal(false);
  protected readonly errorMessage = signal('');
  protected readonly showPassword = signal(false);

  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);

  protected form = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  get username() { return this.form.get('username'); }
  get password() { return this.form.get('password'); }

  togglePassword(): void {
    this.showPassword.update(show => !show);
  }

  async onSubmit(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set('');

    this.authService
      .doLogin(this.form.value.username, this.form.value.password)
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe({
        next: _ => {
          this.router.navigateByUrl('/home');
        },
        error: _ => {
          this.errorMessage.set('Incorrect username or password. Please try again.');
        },
      });
  }
}
