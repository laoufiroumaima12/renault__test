export interface Vehicle {
  id: number;
  brand: string;
  fabricationYear: number;
  fuelType: FuelType;
  garageId: number;
}

export enum FuelType {
  DIESEL = 'DIESEL',
  GASOLINE = 'GASOLINE',
  ELECTRIC = 'ELECTRIC',
  HYBRID = 'HYBRID'
}