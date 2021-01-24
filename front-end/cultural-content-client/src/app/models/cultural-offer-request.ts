import { Location } from './culutral-offer-location';

export interface CulturalOfferRequest {
  name: String;
  description: String;
  location: Location;
  images: String[];
}
