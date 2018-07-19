import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SaleInvoicePayment } from 'app/shared/model/sale-invoice-payment.model';
import { SaleInvoicePaymentService } from './sale-invoice-payment.service';
import { SaleInvoicePaymentComponent } from './sale-invoice-payment.component';
import { SaleInvoicePaymentDetailComponent } from './sale-invoice-payment-detail.component';
import { SaleInvoicePaymentUpdateComponent } from './sale-invoice-payment-update.component';
import { SaleInvoicePaymentDeletePopupComponent } from './sale-invoice-payment-delete-dialog.component';
import { ISaleInvoicePayment } from 'app/shared/model/sale-invoice-payment.model';

@Injectable({ providedIn: 'root' })
export class SaleInvoicePaymentResolve implements Resolve<ISaleInvoicePayment> {
    constructor(private service: SaleInvoicePaymentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((saleInvoicePayment: HttpResponse<SaleInvoicePayment>) => saleInvoicePayment.body));
        }
        return of(new SaleInvoicePayment());
    }
}

export const saleInvoicePaymentRoute: Routes = [
    {
        path: 'sale-invoice-payment',
        component: SaleInvoicePaymentComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SaleInvoicePayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice-payment/:id/view',
        component: SaleInvoicePaymentDetailComponent,
        resolve: {
            saleInvoicePayment: SaleInvoicePaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoicePayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice-payment/new',
        component: SaleInvoicePaymentUpdateComponent,
        resolve: {
            saleInvoicePayment: SaleInvoicePaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoicePayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice-payment/:id/edit',
        component: SaleInvoicePaymentUpdateComponent,
        resolve: {
            saleInvoicePayment: SaleInvoicePaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoicePayments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saleInvoicePaymentPopupRoute: Routes = [
    {
        path: 'sale-invoice-payment/:id/delete',
        component: SaleInvoicePaymentDeletePopupComponent,
        resolve: {
            saleInvoicePayment: SaleInvoicePaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoicePayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
