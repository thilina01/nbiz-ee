import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';
import { SaleInvoiceItemService } from './sale-invoice-item.service';
import { ISaleInvoice } from 'app/shared/model/sale-invoice.model';
import { SaleInvoiceService } from 'app/entities/sale-invoice';
import { IPurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';
import { PurchaseInvoiceItemService } from 'app/entities/purchase-invoice-item';

@Component({
    selector: 'jhi-sale-invoice-item-update',
    templateUrl: './sale-invoice-item-update.component.html'
})
export class SaleInvoiceItemUpdateComponent implements OnInit {
    private _saleInvoiceItem: ISaleInvoiceItem;
    isSaving: boolean;

    saleinvoices: ISaleInvoice[];

    purchaseinvoiceitems: IPurchaseInvoiceItem[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private saleInvoiceItemService: SaleInvoiceItemService,
        private saleInvoiceService: SaleInvoiceService,
        private purchaseInvoiceItemService: PurchaseInvoiceItemService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ saleInvoiceItem }) => {
            this.saleInvoiceItem = saleInvoiceItem;
        });
        this.saleInvoiceService.query().subscribe(
            (res: HttpResponse<ISaleInvoice[]>) => {
                this.saleinvoices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.purchaseInvoiceItemService.query().subscribe(
            (res: HttpResponse<IPurchaseInvoiceItem[]>) => {
                this.purchaseinvoiceitems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.saleInvoiceItem.id !== undefined) {
            this.subscribeToSaveResponse(this.saleInvoiceItemService.update(this.saleInvoiceItem));
        } else {
            this.subscribeToSaveResponse(this.saleInvoiceItemService.create(this.saleInvoiceItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISaleInvoiceItem>>) {
        result.subscribe((res: HttpResponse<ISaleInvoiceItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSaleInvoiceById(index: number, item: ISaleInvoice) {
        return item.id;
    }

    trackPurchaseInvoiceItemById(index: number, item: IPurchaseInvoiceItem) {
        return item.id;
    }
    get saleInvoiceItem() {
        return this._saleInvoiceItem;
    }

    set saleInvoiceItem(saleInvoiceItem: ISaleInvoiceItem) {
        this._saleInvoiceItem = saleInvoiceItem;
    }
}
