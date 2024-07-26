interface GETManyRes<T> {
  rows: T[];
  pagination: {
    page: number;
    size: number;
    pages: number;
    count: number;
    pageLimitReached: boolean;
  }
}

export interface OrdersGETManyRes extends GETManyRes<Order> { }

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
}

export interface Car {
  id: number;
  vin: string;
  make: Make;
  model: Model;
  owner: Owner;
}

export interface CarsGETManyRes extends GETManyRes<Car> { }
