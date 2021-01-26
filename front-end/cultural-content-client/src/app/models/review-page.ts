import { Review } from './review';

export interface ReviewPage {
    content: Review[];
    totalElements: number;
    first: boolean;
    last: boolean;
    totalPages: number;
}
