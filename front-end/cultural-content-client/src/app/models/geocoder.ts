export interface Geocoder{
  query: string[];
  features: GeoFeature[];
}

export interface GeoFeature{
  text: string;
  place_name: string;
  bbox: any[];
  center: number[];
}
