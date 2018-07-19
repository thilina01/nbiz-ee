import { IItem } from 'app/shared/model//item.model';

export interface IBrand {
    id?: number;
    code?: string;
    name?: string;
    items?: IItem[];
}

export class Brand implements IBrand {
    constructor(public id?: number, public code?: string, public name?: string, public items?: IItem[]) {}
}
