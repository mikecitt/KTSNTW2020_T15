import {CulturalOfferLocation} from './culutral-offer-location'

export interface CulturalOfferResponse {
  _id?:any;
  name: String;
  description: String;
  location: CulturalOfferLocation;
  images: String[];

}
