import { CulturalOfferType } from "./cultural-offer-type";

export interface TypePage {
  content: CulturalOfferType[],
  totalElements: number,
  first: boolean,
  last: boolean,
  totalPages: number
}
