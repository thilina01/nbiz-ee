import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SaleInvoice } from 'app/shared/model/sale-invoice.model';
import { SaleInvoiceService } from './sale-invoice.service';
import { SaleInvoiceComponent } from './sale-invoice.component';
import { SaleInvoiceDetailComponent } from './sale-invoice-detail.component';
import { SaleInvoiceUpdateComponent } from './sale-invoice-update.component';
import { SaleInvoiceDeletePopupComponent } from './sale-invoice-delete-dialog.component';
import { ISaleInvoice } from 'app/shared/model/sale-invoice.model';

@Injectable({ providedIn: 'root' })
export class SaleInvoiceResolve implements Resolve<ISaleInvoice> {
    constructor(private service: SaleInvoiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((saleInvoice: HttpResponse<SaleInvoice>) => saleInvoice.body));
        }
        return of(new SaleInvoice());
    }
}

export const saleInvoiceRoute: Routes = [
    {
        path: 'sale-invoice',
        component: SaleInvoiceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SaleInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice/:id/view',
        component: SaleInvoiceDetailComponent,
        resolve: {
            saleInvoice: SaleInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice/new',
        component: SaleInvoiceUpdateComponent,
        resolve: {
            saleInvoice: SaleInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-invoice/:id/edit',
        component: SaleInvoiceUpdateComponent,
        resolve: {
            saleInvoice: SaleInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saleInvoicePopupRoute: Routes = [
    {
        path: 'sale-invoice/:id/delete',
        component: SaleInvoiceDeletePopupComponent,
        resolve: {
            saleInvoice: SaleInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SaleInvoices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
