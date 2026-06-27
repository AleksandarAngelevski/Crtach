import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormControl, ValidationErrors, AbstractControl} from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons'
import { PasswordInput } from "../shared/password-input/password-input";
import { DateService } from '../shared/services/date.service';
import { RegisterRequest } from '../models/register-request.model';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, FontAwesomeModule, PasswordInput],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {
  hidePassword = true;
  registerGroup : FormGroup;

  faEye = faEye;
  faEyeSlash = faEyeSlash;
  
  constructor( private fb: FormBuilder, private dateService: DateService, private authService: AuthService, private router: Router){
    this.registerGroup = this.fb.group(
      {
        fname: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(20)],],
        lname: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(20)], ],
        username: ["", [Validators.required, Validators.minLength(5), Validators.maxLength(20)],],
        email: ["",[Validators.email, Validators.required]],
        password1: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(20)], ],
        password2: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(20), ]],
        birthDate: ["", Validators.required, ],
      }
    , { validators: [passwordMatch, validDate]})
  }

  get password1() { return this.registerGroup.get('password1') as FormControl; }
  get password2() { return this.registerGroup.get('password2') as FormControl; }
  get bdate() { return this.registerGroup.get("birthDate"); }  
  get fname() { return this.registerGroup.get("fname"); }
  get lname() { return this.registerGroup.get("lname"); }
  get uname() { return this.registerGroup.get("username"); }

  togglePasswordVisibility(): void{
    this.hidePassword = !this.hidePassword;
  }
  onSubmit() {
    this.registerGroup.markAllAsTouched();
    if(this.registerGroup.invalid) return;

    const dto : RegisterRequest = {
      firstName: this.registerGroup.value.fname,
      lastName: this.registerGroup.value.lname,
      username: this.registerGroup.value.username,
      email: this.registerGroup.value.email,
      password: this.registerGroup.value.password2,
      role: 'USER',
      birthDate: this.dateService.formatToDisplay(this.registerGroup.value.birthDate),
    };

    this.authService.register(dto).subscribe({
        next:() => this.router.navigate(['/login']),
        error: (err: HttpErrorResponse) => console.error(err)
      });
    return "" ;
  }
}

function passwordMatch(control: AbstractControl): ValidationErrors | null {
  const p1 = control.get("password1")?.value;
  const p2 = control.get("password2")?.value;
  return p1 == p2 ? null : {passwordMismatch: true};
}

function validDate(control: AbstractControl): ValidationErrors | null {
  const bdateField = control.get("birthDate")!;
  let today = Date.now();
  let fieldDate = new Date(bdateField.value);
  if (isNaN(fieldDate.getTime())) return null;
  return today < fieldDate.getTime()? {invalidDate: true} : null;
}


