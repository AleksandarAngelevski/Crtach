import { Component, Input} from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons'

@Component({
  selector: 'app-password-input',
  imports: [ReactiveFormsModule, FontAwesomeModule],
  template: `
  <div [class]="className">
      <input [type]="hide ? 'password' : 'text'" [formControl]="control" [placeholder]="placeholder"/>
      <button type="button" class="toggle-button" (click)="hide = !hide">
        @if (hide) { <fa-icon [icon]="faEye"></fa-icon> }
        @else { <fa-icon [icon]="faEyeSlash"></fa-icon> }
      </button>
    </div>
  `,
  styleUrl: './password-input.css',
})
export class PasswordInput {
  @Input() className = '';
  @Input() control!: FormControl;
  @Input() placeholder = 'Password';
  hide = true;
  faEye = faEye;
  faEyeSlash = faEyeSlash;
}
