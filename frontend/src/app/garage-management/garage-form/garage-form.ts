import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { GarageService } from '../service/garage-service';
import { CommonModule } from '@angular/common';
import { Garage } from '../model/garage';

@Component({
  selector: 'app-garage-form',
  imports: [CommonModule, FormsModule],
  standalone: true,
  templateUrl: './garage-form.html',
  styleUrl: './garage-form.css',
})
export class GarageForm implements OnInit {
  
garageLoaded = false;

  @Input() inputGarage?: Garage;
  title: string = "Add New Garage";
  garage : any = {
    name: '',
    email: '',
    phoneNumber: '',
    address: ''
  };

  constructor(private activeModal: NgbActiveModal) {}
  

  ngOnInit(): void {
    if(this.inputGarage) {
      this.title = "Edit Garage";
      this.garage = { ...this.inputGarage };
    }
  }


  submit() {
    this.activeModal.close(this.garage);
  }

  cancel() {
    this.activeModal.dismiss();
  }


}
