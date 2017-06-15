import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Hotlist } from './hotlist.model';
import { HotlistService } from './hotlist.service';

@Injectable()
export class HotlistPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private hotlistService: HotlistService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.hotlistService.find(id).subscribe((hotlist) => {
                this.hotlistModalRef(component, hotlist);
            });
        } else {
            return this.hotlistModalRef(component, new Hotlist());
        }
    }

    hotlistModalRef(component: Component, hotlist: Hotlist): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hotlist = hotlist;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
