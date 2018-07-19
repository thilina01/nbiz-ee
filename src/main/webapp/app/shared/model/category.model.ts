import { IItem } from 'app/shared/model//item.model';

export interface ICategory {
    id?: number;
    code?: string;
    name?: string;
    items?: IItem[];
}

export class Category implements ICategory {
    constructor(public id?: number, public code?: string, public name?: string, public items?: IItem[]) {}
}
