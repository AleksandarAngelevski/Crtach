import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormControl, ValidationErrors, AbstractControl} from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons'
import { PasswordInput } from "../shared/password-input/password-input";
import { NgIf } from "../../../node_modules/@angular/common/types/_common_module-chunk";
import { filter } from 'rxjs';

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
  
  constructor( private fb: FormBuilder){
    this.registerGroup = this.fb.group(
      {
        fname: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(20)],],
        lname: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(20)], ],
        username: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(20)],],
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
    const [year, month, day] = this.registerGroup.value["birthDate"].split('-');
    this.registerGroup.value["birthDate"] = `${day}/${month}/${year}`;
    console.log(this.registerGroup.value);
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
