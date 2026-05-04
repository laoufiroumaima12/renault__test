import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Vehicle } from './model/vehicle';


@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  private static readonly API_URL = 'http://localhost:8082/api/vehicle';
  constructor(private http: HttpClient) { }

 
  getAllByGarage(garageId: number): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${VehicleService.API_URL}/by-garage/${garageId}`);
  }

  create(vehicle: Vehicle, garageId: number): Observable<Vehicle> {
       
    const params = new HttpParams().set('garageId', garageId.toString());

    return this.http.post<Vehicle>(VehicleService.API_URL, vehicle, { params });
  }

  update(vehicle: Vehicle, id: number): Observable<Vehicle> {
    return this.http.put<Vehicle>(`${VehicleService.API_URL}/${id}`, vehicle);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${VehicleService.API_URL}/${id}`);
  }
}
