import { Routes } from '@angular/router';
import { VehicleManagement } from './vehicle-management/vehicle-management';
import { GarageManagement } from './garage-management/garage-management';

export const routes: Routes = [
   
  { path: '', component: GarageManagement },
  { path: 'vehicles/:garageId', component: VehicleManagement },

];
