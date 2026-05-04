import { Vehicle } from "../../vehicle-management/model/vehicle";

export interface Garage {
    id: number;
    name: string;
    email: string;
    phoneNumber: number;
    address: string;
    vehicles:Vehicle[];
    openningHours:Record<DayOfWeek, OpeningTime | null>
}


export interface OpeningTime {
  startTime: string;
  endTime: string; 
}



export enum DayOfWeek {
  MONDAY = 'MONDAY',
  TUESDAY = 'TUESDAY',
  WEDNESDAY = 'WEDNESDAY',
  THURSDAY = 'THURSDAY',
  FRIDAY = 'FRIDAY',
  SATURDAY = 'SATURDAY',
  SUNDAY = 'SUNDAY'
}
