import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ISaleInvoice, SaleInvoice } from 'app/shared/model/sale-invoice.model';
import { SaleInvoiceService } from './sale-invoice.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';
import { ISalesPerson } from 'app/shared/model/sales-person.model';
import { SalesPersonService } from 'app/entities/sales-person';
import { ISaleInvoiceItem, SaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';
import { PurchaseInvoiceItemService } from 'app/entities/purchase-invoice-item';
import { IPurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';

@Component({
    selector: 'jhi-sale-invoice-update',
    templateUrl: './sale-invoice-update.component.html'
})
export class SaleInvoiceUpdateComponent implements OnInit {
    private _saleInvoice: ISaleInvoice;
    private saleInvoiceItem: ISaleInvoiceItem;
    isSaving: boolean;

    customers: ICustomer[];

    salespeople: ISalesPerson[];
    invoiceDate: string;

    purchaseinvoiceitems: IPurchaseInvoiceItem[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private saleInvoiceService: SaleInvoiceService,
        private customerService: CustomerService,
        private salesPersonService: SalesPersonService,
        private purchaseInvoiceItemService: PurchaseInvoiceItemService,
        private activatedRoute: ActivatedRoute
    ) { }

    ngOnInit() {
        this.saleInvoiceItem = new SaleInvoiceItem();
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ saleInvoice }) => {
            this.saleInvoice = saleInvoice;
        });
        this.customerService.query().subscribe(
            (res: HttpResponse<ICustomer[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.salesPersonService.query().subscribe(
            (res: HttpResponse<ISalesPerson[]>) => {
                this.salespeople = res.body;
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

    addSaleInvoiceItem() {
        if (this.saleInvoice.saleInvoiceItems === undefined) {
            this.saleInvoice.saleInvoiceItems = [];
        }
        this.saleInvoice.amount = this.saleInvoice.amount === undefined ? 0 : this.saleInvoice.amount;
        this.saleInvoice.saleInvoiceItems.push(this.saleInvoiceItem);
        this.saleInvoice.amount += this.saleInvoiceItem.quantity * this.saleInvoiceItem.sellingPrice;
        this.saleInvoiceItem = new SaleInvoiceItem();
    }

    deleteSaleInvoiceItem(i: number) {
        this.saleInvoice.amount -= this.saleInvoice.saleInvoiceItems[i].quantity * this.saleInvoice.saleInvoiceItems[i].sellingPrice;
        this.saleInvoice.saleInvoiceItems.splice(i, 1);
    }

    save() {
        if (this.saleInvoice.saleInvoiceItems.length > 0) {
            this.isSaving = true;
            this.saleInvoice.invoiceDate = moment(new Date(), DATE_TIME_FORMAT);//this.invoiceDate        
            if (this.saleInvoice.id !== undefined) {
                this.subscribeToSaveResponse(this.saleInvoiceService.update(this.saleInvoice));
            } else {
                this.subscribeToSaveResponse(this.saleInvoiceService.create(this.saleInvoice));
            }
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISaleInvoice>>) {
        result.subscribe((res: HttpResponse<ISaleInvoice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.saleInvoice = new SaleInvoice();
        // this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }

    trackSalesPersonById(index: number, item: ISalesPerson) {
        return item.id;
    }
    get saleInvoice() {
        return this._saleInvoice;
    }

    set saleInvoice(saleInvoice: ISaleInvoice) {
        this._saleInvoice = saleInvoice;
        this.invoiceDate = moment(saleInvoice.invoiceDate).format(DATE_TIME_FORMAT);
    }
}
