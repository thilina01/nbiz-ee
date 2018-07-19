import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItem } from 'app/shared/model/item.model';

@Component({
    selector: 'jhi-item-detail',
    templateUrl: './item-detail.component.html'
})
export class ItemDetailComponent implements OnInit {
    item: IItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ item }) => {
            this.item = item;
        });
    }

    previousState() {
        window.history.back();
    }
}
