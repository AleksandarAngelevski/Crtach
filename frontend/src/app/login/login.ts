import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEyeSlash, faEye } from '@fortawesome/free-solid-svg-icons';
import { PasswordInput } from "../shared/password-input/password-input";

type LoginDto = {
  username: string;
  password: string;
};

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: true,
  imports: [FontAwesomeModule, ReactiveFormsModule, PasswordInput],
})
export class LoginComponent {
  usernameError = '';
  passwordError = '';
  error = '';
  hidePassword = true;
  errFlag = false;

  faEye = faEye;
  faEyeSlash = faEyeSlash;

  constructor(private http: HttpClient, private router: Router) {}

  fg = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  login() {
    console.log(this.fg.value);
  }

  get password() { return this.fg.get("password") as FormControl;}

}