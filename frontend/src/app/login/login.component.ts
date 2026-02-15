import {Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../services/auth.service';

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

  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    })
  }

  protected onSubmit(): void {
    console.log("Form submitted");

    if (this.form.invalid) {
      return;
    }

    this.authService
      .login(this.form.value.username, this.form.value.password)
      .subscribe(response => {
        console.log(response);
        localStorage.setItem('access_token', JSON.stringify(response.token));
      });
  }
}
