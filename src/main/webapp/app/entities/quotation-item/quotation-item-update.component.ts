import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuotationItem } from 'app/shared/model/quotation-item.model';
import { QuotationItemService } from './quotation-item.service';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from 'app/entities/quotation';
import { IPurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';
import { PurchaseInvoiceItemService } from 'app/entities/purchase-invoice-item';

@Component({
    selector: 'jhi-quotation-item-update',
    templateUrl: './quotation-item-update.component.html'
})
export class QuotationItemUpdateComponent implements OnInit {
    private _quotationItem: IQuotationItem;
    isSaving: boolean;

    quotations: IQuotation[];

    purchaseinvoiceitems: IPurchaseInvoiceItem[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private quotationItemService: QuotationItemService,
        private quotationService: QuotationService,
        private purchaseInvoiceItemService: PurchaseInvoiceItemService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quotationItem }) => {
            this.quotationItem = quotationItem;
        });
        this.quotationService.query().subscribe(
            (res: HttpResponse<IQuotation[]>) => {
                this.quotations = res.body;
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
        if (this.quotationItem.id !== undefined) {
            this.subscribeToSaveResponse(this.quotationItemService.update(this.quotationItem));
        } else {
            this.subscribeToSaveResponse(this.quotationItemService.create(this.quotationItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuotationItem>>) {
        result.subscribe((res: HttpResponse<IQuotationItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackQuotationById(index: number, item: IQuotation) {
        return item.id;
    }

    trackPurchaseInvoiceItemById(index: number, item: IPurchaseInvoiceItem) {
        return item.id;
    }
    get quotationItem() {
        return this._quotationItem;
    }

    set quotationItem(quotationItem: IQuotationItem) {
        this._quotationItem = quotationItem;
    }
}
