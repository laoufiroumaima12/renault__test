import { Component, OnInit } from '@angular/core';
import { AgGridModule } from 'ag-grid-angular';
import { ColDef, GridApi, GridReadyEvent } from 'ag-grid-community';
import { GarageService } from './service/garage-service';
import { PageRequest } from './model/page-request';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GarageForm } from './garage-form/garage-form';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-garage-management',
  imports: [AgGridModule],
  standalone: true,
  templateUrl: './garage-management.html',
  styleUrl: './garage-management.css',
})
export class GarageManagement {
  
private gridApi!: GridApi;

  pageSize = 10;
  currentPage = 0;
  totalElements = 0;

  
selectedRow: any | null = null;

  
  columnDefs: ColDef[] = [
    
      { headerName: '', checkboxSelection: true, headerCheckboxSelection: true, width: 50, pinned: 'left'},
      { field: 'name', headerName: 'Garage Name', sortable: true, filter: true },
      { field: 'email', headerName: 'Email Address', sortable: true, filter: true },
      { field: 'phoneNumber', headerName: 'Phone Number', sortable: true, filter: true },
      { field: 'address', headerName: 'Address', sortable: true, filter: true},
      { field: 'openingHours',headerName: 'Opening Times', autoHeight: true,
        cellRenderer: (params: any) => {
            const openingTimes = params.value;
            if (!openingTimes) {
              return '-';
            }
            return Object.entries(openingTimes)
              .filter(([_, value]) => value)
              .map(([day, value]: any) => {
                return `${day}: ${value.startTime} - ${value.endTime}`;
              })
              .join('<br/>'); 
          }
      }
];
  rowData: any[] = [];
  defaultColDef: ColDef = {
    resizable: true,
    sortable: true,
    filter: true
  };

  constructor(
    private garageService: GarageService,
    private modalService: NgbModal,
    private router: Router) 
    {}

  onGridReady(params: GridReadyEvent) {
    this.gridApi = params.api;
    this.loadPage(0);
  }

  
onSelectionChanged() {
  const rows = this.gridApi.getSelectedRows();
  this.selectedRow = rows.length ? rows[0] : null;

  console.log('Ligne sélectionnée :', this.selectedRow);
}


  onPaginationChanged() {
    const newPage = this.gridApi.paginationGetCurrentPage();
    if (newPage !== this.currentPage) {
      this.loadPage(newPage);
    }
  }

  loadPage(page: number) {
    const pageRequest : PageRequest = {
      page: page,
      size: this.pageSize,
      sortBy: 'name',
      sortDirection: 'ASC'
    };
    this.currentPage = page;

    this.garageService.getALL(pageRequest).subscribe(res => {
      this.totalElements = res.totalElements;
      this.gridApi.setGridOption('rowData', res.content);
      this.gridApi.setGridOption('paginationPageSize', this.pageSize);
    });
  }

  openFormGarage() {
    const modalRef = this.modalService.open(GarageForm);
    modalRef.result.then((result) => {
      this.garageService.create(result).subscribe(() => {
        this.loadPage(this.currentPage);
      })
    });

  }
  
  editGarage() {
    const modalRef = this.modalService.open(GarageForm);
    const selectedRows = this.gridApi.getSelectedRows();
    const selectedRow = selectedRows[0];
    modalRef.componentInstance.garageId = selectedRow.id;
    modalRef.result.then((result) => {
      this.garageService.update(result, selectedRow.id).subscribe(() => {
        this.loadPage(this.currentPage);
      })
    })
  }

  deleteGarage() {
    const selectedRows = this.gridApi.getSelectedRows();
    const selectedRow = selectedRows[0];
    this.garageService.delete(selectedRow.id).subscribe(() => {
      this.loadPage(this.currentPage);
    });
  }

  toVehicles(){
    const selectedRows = this.gridApi.getSelectedRows();
    const selectedRow = selectedRows[0];
    this.router.navigate(['/vehicles', selectedRow.id]);
  }
}
