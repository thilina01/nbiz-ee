import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPurchaseInvoice } from 'app/shared/model/purchase-invoice.model';
import { PurchaseInvoiceService } from './purchase-invoice.service';
import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from 'app/entities/supplier';

@Component({
    selector: 'jhi-purchase-invoice-update',
    templateUrl: './purchase-invoice-update.component.html'
})
export class PurchaseInvoiceUpdateComponent implements OnInit {
    private _purchaseInvoice: IPurchaseInvoice;
    isSaving: boolean;

    suppliers: ISupplier[];
    invoiceDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private purchaseInvoiceService: PurchaseInvoiceService,
        private supplierService: SupplierService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseInvoice }) => {
            this.purchaseInvoice = purchaseInvoice;
        });
        this.supplierService.query().subscribe(
            (res: HttpResponse<ISupplier[]>) => {
                this.suppliers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.purchaseInvoice.invoiceDate = moment(this.invoiceDate, DATE_TIME_FORMAT);
        if (this.purchaseInvoice.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseInvoiceService.update(this.purchaseInvoice));
        } else {
            this.subscribeToSaveResponse(this.purchaseInvoiceService.create(this.purchaseInvoice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseInvoice>>) {
        result.subscribe((res: HttpResponse<IPurchaseInvoice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSupplierById(index: number, item: ISupplier) {
        return item.id;
    }
    get purchaseInvoice() {
        return this._purchaseInvoice;
    }

    set purchaseInvoice(purchaseInvoice: IPurchaseInvoice) {
        this._purchaseInvoice = purchaseInvoice;
        this.invoiceDate = moment(purchaseInvoice.invoiceDate).format(DATE_TIME_FORMAT);
    }
}
