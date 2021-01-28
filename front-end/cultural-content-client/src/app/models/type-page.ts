import { CulturalOfferType } from './cultural-offer-type';
import { CulturalOfferSubType } from './culutral-offer-subType';

export interface TypePage {
  content: CulturalOfferType[];
  totalElements: number;
  first: boolean;
  last: boolean;
  totalPages: number;
}

export interface SubTypePage {
  content: CulturalOfferSubType[];
  totalElements: number;
  first: boolean;
  last: boolean;
  totalPages: number;
}
