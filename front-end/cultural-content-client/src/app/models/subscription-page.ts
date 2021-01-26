import { Subscription } from './subscription';

export interface SubscriptionPage {
    content: Subscription[];
    totalElements: number;
    first: boolean;
    last: boolean;
    totalPages: number;
}
