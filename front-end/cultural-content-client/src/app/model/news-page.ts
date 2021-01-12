import { NewsResponse } from "./news-response";

export interface NewsPage {
    content: NewsResponse[],
    totalElements: number,
    first: boolean,
    last: boolean,
    totalPages: number
}