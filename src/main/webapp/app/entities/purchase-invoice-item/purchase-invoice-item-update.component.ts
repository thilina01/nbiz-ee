import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';
import { PurchaseInvoiceItemService } from './purchase-invoice-item.service';
import { IPurchaseInvoice } from 'app/shared/model/purchase-invoice.model';
import { PurchaseInvoiceService } from 'app/entities/purchase-invoice';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';

@Component({
    selector: 'jhi-purchase-invoice-item-update',
    templateUrl: './purchase-invoice-item-update.component.html'
})
export class PurchaseInvoiceItemUpdateComponent implements OnInit {
    private _purchaseInvoiceItem: IPurchaseInvoiceItem;
    isSaving: boolean;

    purchaseinvoices: IPurchaseInvoice[];

    items: IItem[];
    expiaryDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private purchaseInvoiceItemService: PurchaseInvoiceItemService,
        private purchaseInvoiceService: PurchaseInvoiceService,
        private itemService: ItemService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseInvoiceItem }) => {
            this.purchaseInvoiceItem = purchaseInvoiceItem;
        });
        this.purchaseInvoiceService.query().subscribe(
            (res: HttpResponse<IPurchaseInvoice[]>) => {
                this.purchaseinvoices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.itemService.query().subscribe(
            (res: HttpResponse<IItem[]>) => {
                this.items = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.purchaseInvoiceItem.expiaryDate = moment(this.expiaryDate, DATE_TIME_FORMAT);
        if (this.purchaseInvoiceItem.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseInvoiceItemService.update(this.purchaseInvoiceItem));
        } else {
            this.subscribeToSaveResponse(this.purchaseInvoiceItemService.create(this.purchaseInvoiceItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseInvoiceItem>>) {
        result.subscribe((res: HttpResponse<IPurchaseInvoiceItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPurchaseInvoiceById(index: number, item: IPurchaseInvoice) {
        return item.id;
    }

    trackItemById(index: number, item: IItem) {
        return item.id;
    }
    get purchaseInvoiceItem() {
        return this._purchaseInvoiceItem;
    }

    set purchaseInvoiceItem(purchaseInvoiceItem: IPurchaseInvoiceItem) {
        this._purchaseInvoiceItem = purchaseInvoiceItem;
        this.expiaryDate = moment(purchaseInvoiceItem.expiaryDate).format(DATE_TIME_FORMAT);
    }
}
