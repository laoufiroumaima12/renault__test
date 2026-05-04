import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { ColDef, GridApi, GridReadyEvent } from 'ag-grid-community';
import { VehicleService } from './vehicle-service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { VehicleForm } from './vehicle-form/vehicle-form';
import { Subject } from 'rxjs/internal/Subject';
import { Garage } from '../garage-management/model/garage';
import { Vehicle } from './model/vehicle';

@Component({
  selector: 'app-vehicle-management',
  imports: [AgGridModule],
  templateUrl: './vehicle-management.html',
  styleUrl: './vehicle-management.css',
})
export class VehicleManagement implements OnInit, OnDestroy {

  
  private destroy$ = new Subject<void>();
  private gridApi!: GridApi;
  private garageId!: number;
  selectedRow: any | null = null;
  columnDefs: ColDef[] = [
      
        { headerName: '', checkboxSelection: true, headerCheckboxSelection: true, width: 50, pinned: 'left'},
        { field: 'brand', headerName: 'Brand', sortable: true, filter: true },
        { field: 'fabricationYear', headerName: 'Fabrication Year', sortable: true, filter: true },
        { field: 'fuelType', headerName: 'Fuel Type', sortable: true, filter: true },
      ];
  rowData: any[] = [];
  defaultColDef: ColDef = {
      resizable: true,
      sortable: true,
      filter: true
  };

  constructor(private activeRoute: ActivatedRoute,
              private vehicleService: VehicleService,
              private modalService: NgbModal,
              private router: Router) 
    { }

  ngOnInit(): void {
    
    const garageId = Number(
      this.activeRoute.snapshot.paramMap.get('garageId')
    );
    this.garageId = garageId;
    this.loadVehicles(garageId);
  }

  private loadVehicles(garageId: number) { 
    this.vehicleService.getAllByGarage(garageId).subscribe(result => {
      this.rowData = [...result];
      if(this.gridApi) {
        this.gridApi.setGridOption('rowData', this.rowData);
      }
      });
  }

  onGridReady(params: GridReadyEvent) {
      this.gridApi = params.api;
  }
  
  
  getRowId(params: any) {
    return params.data.id;
  }

    
  onSelectionChanged() {
    const rows = this.gridApi.getSelectedRows();
    this.selectedRow = rows.length ? rows[0] : null;
  
    console.log('Ligne sélectionnée :', this.selectedRow);
  }

  addVehicle() {
    const modalRef = this.modalService.open(VehicleForm);
    modalRef.result.then((result: Vehicle) => {
      if (result) {
        this.vehicleService.create(result, this.garageId).subscribe((createdVehicle) => {
          this.rowData.push(createdVehicle);
          this.gridApi.applyTransaction({
            add: [createdVehicle]
          });

        });
      }

      });
  }
    
  

  editVehicle() {
    const modalRef = this.modalService.open(VehicleForm);
    modalRef.componentInstance.vehicle = { ...this.selectedRow };;
    modalRef.result.then((result) => {
      if (result) {
        this.vehicleService.update(result, this.selectedRow.id).subscribe((updatedVehicle) => {
          const index = this.rowData.findIndex(v => v.id === updatedVehicle.id);
          this.rowData[index] = updatedVehicle;
          this.gridApi.applyTransaction({
            update: [updatedVehicle]
          });
        });
        this.gridApi.deselectAll();
      }
    });
  }

  deleteVehicle() {
    const selectedRows = this.gridApi.getSelectedRows();
    const selectedRow = selectedRows[0];  
    this.vehicleService.delete(selectedRow.id).subscribe(() => {
      this.rowData = this.rowData.filter(
      v => v.id !== selectedRow.id
    );
    this.gridApi.applyTransaction({
      remove: [selectedRow]
    });
    this.selectedRow = null;
    })
  }

  goTogarageList() {
    this.router.navigate(['']);
  }

  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  

}
