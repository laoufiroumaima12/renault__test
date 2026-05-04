import { Component, Input, OnInit } from '@angular/core';
import { FuelType, Vehicle } from '../model/vehicle';
import { I } from '@angular/cdk/keycodes';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-vehicle-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './vehicle-form.html',
  styleUrl: './vehicle-form.css',
})
export class VehicleForm implements OnInit {
  @Input() inputVehicle?: Vehicle;
  vehicle: any = {
    brand: '',
    fabricationYear: 2025,
    fuelType: FuelType.DIESEL
  };


  fuelTypeList: FuelType[] = Object.values(FuelType);
  title = "Add New Vehicle";

  constructor(private activeModal: NgbActiveModal) {}
  
  ngOnInit(): void {
    if(this.inputVehicle) {
      this.title = "Edit Vehicle";
      this.vehicle = { ...this.inputVehicle };
    }

  }
  
  submit() {
    this.activeModal.close(this.vehicle);
  }

  cancel() {
    this.activeModal.dismiss();
  }

}
