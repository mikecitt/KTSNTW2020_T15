import { CulturalOfferLocation } from './culutral-offer-location';
import { Location } from './culutral-offer-location';
import { CulturalOfferSubType } from './culutral-offer-subType';

export interface CulturalOfferResponse {
  _id?: any;
  name: String;
  description: String;
  location: Location;
  subType: CulturalOfferSubType;
  images: String[];
}
