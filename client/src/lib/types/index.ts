/* eslint-disable no-use-before-define */
interface Pagination {
  page: number;
  size: number;
  pages: number;
  count: number;
  pageLimitReached: boolean;
}

export interface GETManyRes<T> {
  rows: T[];
  pagination: Pagination;
}

export interface Owner {
  id: number;
  name: string;
  contact: string;
}

export interface Model {
  id: number;
  name: string;
}

export interface Make {
  id: number;
  name: string;
  logoUrl: string;
  models: Model[];
}

export interface Color {
  id: number;
  name: string;
}

export interface Car {
  id: number;
  vin: string;
  make: Make;
  model: Model;
  owner: Owner;
  color: Color;
  year: number;
  price: number;
  mileage: number;
  registrationNumber: string;
  insurancePolicyNumber: string;
  insuranceExpiration: Date;
  registrationExpiration: Date;
  lastMaintenanceDate: Date;
}

export interface CarsGETManyRes extends GETManyRes<Car> { }

export interface ColorsGETManyRes extends GETManyRes<Color> { }

export interface MakesGETManyRes extends GETManyRes<Make> { }

export interface ModelsGETManyRes extends GETManyRes<Model> { }

export interface OwnersGETManyRes extends GETManyRes<Owner> { }
