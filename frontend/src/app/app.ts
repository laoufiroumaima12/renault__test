import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GarageManagement } from './garage-management/garage-management';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `<router-outlet></router-outlet>`
})
export class App {
  protected readonly title = signal('renault_test');
}
