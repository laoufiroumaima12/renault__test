import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Garage } from '../model/garage';
import { PageRequest } from '../model/page-request';

@Injectable({
  providedIn: 'root',
})
export class GarageService {
  private static readonly API_URL = 'http://localhost:8082/api/garage';
  constructor(private http: HttpClient) { }

  getALL(pageRequest: PageRequest):Observable<any> {
    let params = new HttpParams();
    params.append('page', pageRequest?.page?.toString());
    params.append('size', pageRequest?.size?.toString());
    if (pageRequest.sortBy) {
      params.append('sortBy', pageRequest?.sortBy);
      params.append('sortDirection', pageRequest?.sortDirection || 'ASC');
    }

    return this.http.get<any>(GarageService.API_URL+'/all', { params });
  }

  getById(id: number): Observable<Garage> {
    return this.http.get<Garage>(`${GarageService.API_URL}/${id}`);
  }

  create(garage: Garage): Observable<Garage> {
    return this.http.post<Garage>(GarageService.API_URL, garage);
  }

  update(garage: Garage, id: number): Observable<Garage> {
    return this.http.put<Garage>(`${GarageService.API_URL}/${id}`, garage);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${GarageService.API_URL}/${id}`);
  }
}
