export interface CulturalOfferLocation {
  id?:any;
  name: string,
  location: Location
}

export interface Location{
  address: string;
  latitude: number;
  longitude: number;
}
