import { News } from "./news";

export interface NewsPage {
    content: News[],
    totalElements: number,
    first: boolean,
    last: boolean,
    totalPages: number
}