import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DateService {
  formatToDisplay(date: string): string{
    const [ year, month, day] = date.split('-');
    return `${day}/${month}/${year}`;
  }
}
